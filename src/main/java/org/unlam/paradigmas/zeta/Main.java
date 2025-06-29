package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.loaders.GuideLoder;
import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.Loader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.models.Guide;


public class Main {
    public static void main(String[] args) {

        Loader<Inventory> il = new InventoryLoader();
        Loader<RecipeBook> el = new RecipeLoader();
        Loader<Guide> gl = new GuideLoder();

        final Inventory i = il.loadFile();
        final RecipeBook r = el.loadFile();
        final Guide g = gl.loadFile();
        Worker w = new Worker(i, r);

        Menu m = new Menu(w, g);
        m.run();
    }
}