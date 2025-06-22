package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.Loader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;

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
    }
}