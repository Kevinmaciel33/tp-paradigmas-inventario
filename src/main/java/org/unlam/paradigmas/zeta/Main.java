package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.Loader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;

import static org.unlam.paradigmas.zeta.Constants.MANUAL;
import static org.unlam.paradigmas.zeta.Constants.SCOPE;

import static org.unlam.paradigmas.zeta.Constants.MANUAL;
import static org.unlam.paradigmas.zeta.Constants.SCOPE;


public class Main {
    public static void main(String[] args) {

        Loader<Inventory> il = new InventoryLoader();
        Loader<RecipeBook> el = new RecipeLoader();

        final Inventory i = il.loadFile();
        final RecipeBook r = el.loadFile();
        Worker w = new Worker(i, r);

        Menu m;
        String scope = System.getenv(SCOPE);
        if ( scope != null && scope.equalsIgnoreCase(MANUAL) ) {
            m = new Menu(w, g);
        } else {
            m = new Atomatic(w, g);
        }

        m.run();
    }
}