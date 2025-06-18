package org.example;

import org.example.enums.Classification;
import org.example.loaders.InventoryLoader;
import org.example.loaders.Loader;
import org.example.loaders.RecipeLoader;
import org.example.models.Element;
import org.example.models.Recipe;
import org.example.querys.QueryElementsFromZero;

import java.util.concurrent.CompletableFuture;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Loader[] loader = { new InventoryLoader(), new RecipeLoader() };

// TODO idea: hacer hilos para cargar los archivos y crons para que se actualicen cada n segundos
// en caso de que el archivo de error, cargar un backup anterior
        for ( int i = 0; i < loader.length; ++i ) {
            int finalI = i;
            //CompletableFuture.runAsync(() -> {
                loader[finalI].loadFile();
            //});
        }

        final Inventory i = InventoryLoader.getData();
        final RecipeBook r = RecipeLoader.getData();
        Worker w = new Worker(i, r);
        
        /////////////////////////////////////////
        ////////////PRUEBAS PARA MAIN////////////
        ////////////DESPUES SE BORRA/////////////
        /////////////////////////////////////////
        System.out.println("el main funciona :)");

//        System.out.println("Lista de todos los elementos cargados en RecipeBook");
//        System.out.println("----- -- ----- --- --------- -------- -- ----------");
//        
//        int count = 0;
//
//        for (var library : r.libraries) {
//            Recipe recipe = library.recipe();
//
//            System.out.println("Receta: " + recipe.give().name());
//            System.out.println("Tipo: " + recipe.give().type());
//            System.out.println("Ingredientes:");
//            for (Element ingredient : recipe.ingredients()) {
//                System.out.println("- " + ingredient.name() + " (" + ingredient.type() + ")");
//            }
//            System.out.println("------------------------------");
//            count++;
//        }
//        System.out.println("Cant de recetas : "+count);
//        System.out.println("-------------------------------------");
//        System.out.println("-------------------------------------");
        
        
        QueryElementsFromZero query = new QueryElementsFromZero();  

        Element percloricoElement = new Element("PERCLORICO", Classification.ALL);
        Element superElement = new Element("SUPER_ELEMENTO", Classification.ALL);
     
        query.run(percloricoElement, r.libraries);
        System.out.println("-------------------------------------");
        query.run(superElement, r.libraries);
    }
    
    
}