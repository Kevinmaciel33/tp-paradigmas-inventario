package org.unlam.paradigmas.zeta;

import org.unlam.paradigmas.zeta.enums.Classification;
import org.unlam.paradigmas.zeta.enums.QueryEnum;
import org.unlam.paradigmas.zeta.models.*;

import java.util.MissingResourceException;

public class Automatic extends Menu {
    private Worker worker;

    public Automatic(Worker worker, Guide guide) {
        super(worker, guide);
        this.worker = worker;
    }

    @Override
    public void run() {
        System.out.println("#### GUIA ####");
        showGuide();
        System.out.println();

        System.out.println("#### INVENTARIO ####");
        showCurrentInventory();
        System.out.println();

        final Element water = new Element("AGUA");

        System.out.println("#### QUERY BASICA ####");
        Queryable e = this.worker.query(QueryEnum.ELEMENTS, water);
        System.out.println(e.show());
        System.out.println();

        System.out.println("#### CUANTOS PUEDO CREAR ####");
        Queryable hm = this.worker.query(QueryEnum.HOW_MANY_ELEMENTS, water);
        System.out.println(hm.show());
        System.out.println();

        System.out.println("#### ELEMENTOS FALTANTES ####");
        Queryable m = this.worker.query(QueryEnum.MISSING_ELEMENTS, new Element("solucion_limpieza"));
        System.out.println(m.show());
        System.out.println();

        System.out.println("#### ELEMENTOS FALTANTES DESDE CERO ####");
        Queryable mfz = this.worker.query(QueryEnum.MISSING_ELEMENTS_FROM_ZERO, new Element("bicarbonato"));
        System.out.println(mfz.show());
        System.out.println();

        System.out.println("#### ELEMENTOS DESDE CERO ####");
        Queryable fz = this.worker.query(QueryEnum.ELEMENTS_FROM_ZERO, new Element("explosivo_ultimo_nivel"));
        System.out.println(fz.show());
        System.out.println();

        System.out.println("#### CREACION BASE ####");
        MultiRecipe multiRecipe = (MultiRecipe) worker.query(QueryEnum.ELEMENTS, water);
        System.out.println(multiRecipe.show());
        System.out.println();

        System.out.println("#### CREANDO LA PRIMER RECETA ####");
        Recipe re = multiRecipe.libraries.get(0).getValue();
        this.worker.create(water, re);
        System.out.println();

        System.out.println("#### CREANDO LA SEGUNDA RECETA ####");
        Recipe re2 = multiRecipe.libraries.get(1).getValue();
        this.worker.create(water, re2);
        System.out.println();

        System.out.println("#### CREACION SULF FALTA ELEMENTO ####");

        final Element complejo = new Element("SULF_HIERRO");
        Queryable dd = this.worker.query(QueryEnum.ELEMENTS, complejo);
        System.out.println(dd.show());
        System.out.println();
        try {
            multiRecipe = (MultiRecipe) worker.query(QueryEnum.ELEMENTS, complejo);
            final Recipe re3 = multiRecipe.libraries.get(0).getValue();
            this.worker.create(complejo, re3);
            System.out.println();
        } catch (MissingResourceException a) {
            System.out.println(a.getMessage()+" "+a.getKey());
        }

        System.out.println("#### QUERY CATALIZADOR ####");
        final Element mineral = new Element("SAL", Classification.MINERAL);
        Queryable qq = this.worker.query(QueryEnum.ELEMENTS, mineral);
        System.out.println(qq.show());
        System.out.println();

        multiRecipe = (MultiRecipe) worker.query(QueryEnum.ELEMENTS, mineral);
        final Recipe re3 = multiRecipe.libraries.get(0).getValue();
        this.worker.create(mineral, re3);
        System.out.println();

        showCraftHistory();
    }
}
