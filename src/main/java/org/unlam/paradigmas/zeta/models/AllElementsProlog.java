package org.unlam.paradigmas.zeta.models;

import org.unlam.paradigmas.zeta.models.Queryable;
import java.util.List;
import java.util.ArrayList;

public class AllElementsProlog implements Queryable {
    private List<String> elements;
    
    public AllElementsProlog() {
        this.elements = new ArrayList<>();
    }
    
    public AllElementsProlog(List<String> elements) {
        this.elements = new ArrayList<>(elements);
    }
    
    public void addElement(String element) {
        this.elements.add(element);
    }
    
    public List<String> getElements() {
        return new ArrayList<>(elements);
    }
    
    @Override
    public String show() {
        if (elements.isEmpty()) {
            return "No se pueden crear elementos con el inventario actual.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Elementos que se pueden crear:\n");
        for (String element : elements) {
            sb.append("  - ").append(element).append("\n");
        }
        return sb.toString();
    }
} 