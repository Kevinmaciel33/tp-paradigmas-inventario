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
        
        //Pruebas de consultas en main
        QueryElementsFromZero query = new QueryElementsFromZero();  

        Element percloricoElement = new Element("PERCLORICO", Classification.ALL);
        Element superElement = new Element("SUPER_ELEMENTO", Classification.ALL);
     
        query.run(percloricoElement, r.libraries);
        System.out.println("-------------------------------------");
        query.run(superElement, r.libraries);
    }
    
    
}