package org.unlam.paradigmas.zeta.querys;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import org.unlam.paradigmas.zeta.loaders.InventoryJson;
import org.unlam.paradigmas.zeta.loaders.RecipeJson;
import java.util.*;

//Requiere descargar projog y agregarlo en el build path del proyecto
public class PrologRuleGenerator {					

    private static String toPrologAtom(String s) {
    	//Pasa el nombre a snake_case
        return s.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "_");
    }

    
    public static void writeRulesInventoryToFile(InventoryJson[] items, String path, boolean append) throws IOException {                      
        List<String> rules = new ArrayList<>();
        rules.add("% === Hechos de inventario ===");

        for (InventoryJson i : items) {
            String elements = toPrologAtom(i.name);
            int amount = i.quantity;
            rules.add(String.format("tengo(%s, %d).", elements, amount));
        }

        rules.add("");
        Files.write(
            Paths.get(path),
            rules,
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE
        );
    }


    
    public static void writeRulesRecipeToFile(RecipeJson[] recipe, String path, boolean append) throws IOException {
        List<String> reglas = new ArrayList<>();

        reglas.add("% === Reglas de recetas ===");
        //Cuenta la ocurrencia de cada ingrediente
        for (RecipeJson r : recipe) {						
            Map<String, Integer> counts = new LinkedHashMap<>();
            for (String ing : r.elements) {
                String atom = toPrologAtom(ing);
                counts.put(atom, counts.getOrDefault(atom, 0) + 1);
            }
            
          //Separa claves y valores
            String product = toPrologAtom(r.give);					

            StringBuilder elementsBuilder = new StringBuilder();
            StringBuilder amountBuilder = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                if (!first) {
                    elementsBuilder.append(",");
                    amountBuilder.append(",");
                }
                elementsBuilder.append(entry.getKey());
                amountBuilder.append(entry.getValue());
                first = false;
            }

            String elements = elementsBuilder.toString();
            String amount = amountBuilder.toString();

            reglas.add(String.format("receta(%s, [%s], [%s]).", product, elements, amount));
        }
        //línea en blanco
        reglas.add(""); 
        
        //Escribe las reglas en un archivo
        Files.write(					
            Paths.get(path),
            reglas,
            StandardCharsets.UTF_8,
            append ? StandardOpenOption.CREATE : StandardOpenOption.TRUNCATE_EXISTING,
            append ? StandardOpenOption.APPEND : StandardOpenOption.WRITE
        );
    }

    
    
    public static void writeCraftingRulesToFile(String path, boolean append) throws IOException{
        List<String> rules = new ArrayList<>();
        rules.add("% === Reglas de crafteo ===");

        rules.addAll(List.of(
            //Calcula todos los requerimientos totales para crear un producto
            "calcular_requerimientos_totales(Ings, Cants, Requerimientos) :-",
            "	calcular_requerimientos_lista(Ings, Cants, RequerimientosParciales),",
            "	agrupar_requerimientos(RequerimientosParciales, Requerimientos).",
            
            //Calcula requerimientos para una lista de ingredientes
            "calcular_requerimientos_lista([], [], []).",
            "calcular_requerimientos_lista([Ing|Ings], [Cant|Cants], Requerimientos) :-",
            "	( receta(Ing, IngsReceta, CantsReceta) -> ",
            "	multiplicar_cantidades(IngsReceta, CantsReceta, Cant, IngsExpandidos, CantsExpandidos),",
            "	calcular_requerimientos_lista(IngsExpandidos, CantsExpandidos, ReqExpandidos),",
            "	calcular_requerimientos_lista(Ings, Cants, ReqResto),",
            "	append(ReqExpandidos, ReqResto, Requerimientos)",
            "	;",
            "	calcular_requerimientos_lista(Ings, Cants, ReqResto),",
            "	Requerimientos = [(Ing, Cant)|ReqResto]",
            ").",
            
            //Agrupa requerimientos del mismo ingrediente
            "agrupar_requerimientos([], []).",
            "agrupar_requerimientos([(Ing, Cant)|Resto], [(Ing, Total)|Agrupados]) :-",
            "sumar_requerimientos(Ing, Resto, Cant, Total, RestoFiltrado),",
            "agrupar_requerimientos(RestoFiltrado, Agrupados).",
            
            //Suma requerimientos del mismo ingrediente 
            "sumar_requerimientos(_, [], Acum, Acum, []).",
            "sumar_requerimientos(Ing, [(Ing, Cant)|Resto], Acum, Total, RestoFiltrado) :-",
            "	Acum1 is Acum + Cant,",
            "	sumar_requerimientos(Ing, Resto, Acum1, Total, RestoFiltrado).",
            "sumar_requerimientos(Ing, [(Otro, Cant)|Resto], Acum, Acum, [(Otro, Cant)|RestoFiltrado]) :-",
            "	Ing \\= Otro,",
            "	sumar_requerimientos(Ing, Resto, Acum, Acum, RestoFiltrado).",
            
            //Verifica si se puede crear considerando el consumo real de recursos
            "puedo_crear_con_recursos(Ings, Cants) :-",
            "calcular_requerimientos_totales(Ings, Cants, Requerimientos),",
            "verificar_reqs_con_inventario(Requerimientos).",
            
            "puedo_crear(Prod) :-",
            "    receta(Prod, Ings, Cants),",
            "    puedo_crear_con_recursos(Ings, Cants).",
            //Verifica si hay suficientes recursos disponibles
            "verificar_reqs_con_inventario([]).",
            "verificar_reqs_con_inventario([(Ing, Cant)|T]) :-",
            "    tengo(Ing, CantInv),",
            "    CantInv >= Cant,",
            "    verificar_reqs_con_inventario(T).",
            //Multiplica cantidades de una receta por el factor necesario
            "multiplicar_cantidades([], [], _, [], []).",
            "multiplicar_cantidades([Ing|Ings], [Cant|Cants], Factor, [Ing|IngsResult],[CantMult|CantsResult]) :- ",
            "	 CantMult is Cant * Factor,",
            "	multiplicar_cantidades(Ings, Cants, Factor, IngsResult, CantsResult)."
            ));
        //Línea en blanco final
        rules.add(""); 

        Files.write(
            Paths.get(path),
            rules,
            StandardCharsets.UTF_8,
            append ? StandardOpenOption.CREATE : StandardOpenOption.TRUNCATE_EXISTING,
            append ? StandardOpenOption.APPEND : StandardOpenOption.WRITE
        );
    }

}
