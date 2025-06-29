package java.org.unlam.paradigmas.zeta.querys;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.org.unlam.paradigmas.zeta.loaders.InventoryJson;
import java.org.unlam.paradigmas.zeta.loaders.RecipeJson;
import java.util.*;
import java.util.stream.Collectors;

public class PrologRuleGenerator {					//Requiere descargar projog y agregarlo en el build path del proyecto

    private static String toPrologAtom(String s) {								//Pasa el nombre a snake_case
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
            append ? StandardOpenOption.CREATE : StandardOpenOption.TRUNCATE_EXISTING,
            append ? StandardOpenOption.APPEND : StandardOpenOption.WRITE
        );
    }


    
    public static void writeRulesRecipeToFile(RecipeJson[] recipe, String path, boolean append) throws IOException {
        List<String> reglas = new ArrayList<>();

        reglas.add("% === Reglas de recetas ===");
        for (RecipeJson r : recipe) {						//Cuenta la ocurrencia de cada ingrediente
            Map<String, Integer> counts = new LinkedHashMap<>();
            for (String ing : r.elements) {
                String atom = toPrologAtom(ing);
                counts.put(atom, counts.getOrDefault(atom, 0) + 1);
            }

            String product = toPrologAtom(r.give);					//Separa claves y valores
            String elements = String.join(",", counts.keySet());
            String amount = counts.values().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

            reglas.add(String.format("receta(%s, [%s], [%s]).", product, elements, amount));
        }
        reglas.add(""); // línea en blanco
        	
        Files.write(					//Escribe las reglas en un archivo
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
            
            "requerimientos_totales(Prod, Cant, []) :-",			//Veo si esta en el inventario con la cantidad suficiente
            "    tengo(Prod, CantInv),",
            "    CantInv >= Cant.",

            "requerimientos_totales(Prod, Cant, [(Prod, CantRestante)]) :-",			//Elemento base que tengo o no lo suficiente
            "    \\+ receta(Prod, _, _),",
            "    ( \\+ tengo(Prod, _) ; tengo(Prod, CantInv), CantInv < Cant ),",
            "    CantRestante is Cant - CantInv.",

            "requerimientos_totales(Prod, Cant, TotalReqs) :-",						
            "    receta(Prod, Ings, Cants),",
            "    ( tengo(Prod, CantInv), CantInv >= Cant ->",					//Tengo la cantidad suficiente
            "        TotalReqs = []",
            "    ;",
            "        CantFaltante is max(Cant - CantInv, 0),",							//No tengo la cantidad suficiente, desglosa los ingredientes y los agrupa
            "        requerimientos_lista(Ings, Cants, CantFaltante, Parciales),",
            "        agrupar_requerimientos(Parciales, TotalReqs)",
            "    ).",

           
            "requerimientos_lista([], [], _, []).",
            "requerimientos_lista([I|Is], [C|Cs], Factor, Total) :-",
            "    CantNecesaria is C * Factor,",									//Multiplica las cantidades necesarias
            "    requerimientos_totales(I, CantNecesaria, ReqI),",				//Ve si se tiene esa cantidad
            "    requerimientos_lista(Is, Cs, Factor, Resto),",					//Sino baja de nivel de nuevo
            "    append(ReqI, Resto, Total).",									//Une las listas

         
            "agrupar_requerimientos(Lista, Agrupada) :-",							//Tres funciones para agrupar los requisitos
            "    sort(0, @=<, Lista, Ordenada),",
            "    agrupar_requerimientos_ordenada(Ordenada, Agrupada).",

            "agrupar_requerimientos_ordenada([], []).",
            "agrupar_requerimientos_ordenada([(Ing, C)|T], [(Ing, Suma)|Resto]) :-",
            "    juntar_iguales(Ing, T, C, Suma, TResto),",
            "    agrupar_requerimientos_ordenada(TResto, Resto).",

            "juntar_iguales(_, [], Acum, Acum, []).",
            "juntar_iguales(Ing, [(Ing, C2)|T], Acum, Suma, Resto) :-",
            "    Acum1 is Acum + C2,",
            "    juntar_iguales(Ing, T, Acum1, Suma, Resto).",
            "juntar_iguales(Ing, [(Otro, C)|T], Acum, Acum, [(Otro, C)|T]) :-",
            "    Ing \\= Otro.",

            
            "puedo_crear(Prod) :-",
            "    requerimientos_totales(Prod, 1, Reqs),",
            "    verificar_reqs_con_inventario(Reqs).",

            "verificar_reqs_con_inventario([]).",
            "verificar_reqs_con_inventario([(Ing, Cant)|T]) :-",
            "    tengo(Ing, CantInv),",
            "    CantInv >= Cant,",
            "    verificar_reqs_con_inventario(T)."
        ));

        rules.add(""); // Línea en blanco final

        Files.write(
            Paths.get(path),
            rules,
            StandardCharsets.UTF_8,
            append ? StandardOpenOption.CREATE : StandardOpenOption.TRUNCATE_EXISTING,
            append ? StandardOpenOption.APPEND : StandardOpenOption.WRITE
        );
    }

}