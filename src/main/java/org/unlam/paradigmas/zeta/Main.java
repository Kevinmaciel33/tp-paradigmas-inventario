package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.Loader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;

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

        //TODO: para cada consulta/craft se va a hacer un new Element
        // el new element en algunos casos no importa el classification.
        // ...
        // Entonces es un overhead, se reserva memoria de mas para un string.
        // Incluso si importara el classification, se hacen new constantemente para un string + enum
        // nose si implique un problema real que se traduzca en tiempo, pero vale la pena verlo
        // Pense en cargar todos los objetos posibles en un loder aparte
        // ...
        // Asi en vez de hacer un new y que se haga un allocation y una limpieza del GC
        // se hace una consulta a un mapa en memoria.
        // ...
        // lo mismo vi que paso en los catalizadores, se hacia un new para consultar si estaba en el inventario
        // y acto seguido se hacia un remove del inventario. Algo al pedo
        // ...
        // Se puede incluso hacer un benchmark para ver la mejora

    }
}