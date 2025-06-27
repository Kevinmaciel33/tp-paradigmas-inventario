package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.Loader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;


public class Main {
    public static void main(String[] args) {

        Loader<Inventory> il = new InventoryLoader();
        Loader<RecipeBook> el = new RecipeLoader();

        final Inventory i = il.loadFile();
        final RecipeBook r = el.loadFile();
        Worker w = new Worker(i, r);

        Menu.run(w);
    }
}