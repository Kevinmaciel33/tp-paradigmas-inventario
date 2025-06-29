package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.loaders.InventoryLoader;
import org.unlam.paradigmas.zeta.loaders.Loader;
import org.unlam.paradigmas.zeta.loaders.RecipeLoader;
import org.unlam.paradigmas.zeta.querys.PrologRuleGenerator;


public class Main {
    public static void main(String[] args) {

        Loader<Inventory> il = new InventoryLoader();
        Loader<RecipeBook> el = new RecipeLoader();

        final Inventory i = il.loadFile();
        final RecipeBook r = el.loadFile();
        Worker w = new Worker(i, r);

        try{
        	PrologRuleGenerator.writeCraftingRulesToFile("src/base.pl", true);
        }catch(Exception e) {
        	throw new RuntimeException("Error al leer el inventario JSON", e);
        }
        
        Menu.run(w);
    }
}
