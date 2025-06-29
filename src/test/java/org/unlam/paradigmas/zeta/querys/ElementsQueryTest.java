package org.unlam.paradigmas.zeta.querys;

import org.junit.jupiter.api.Test;
import org.unlam.paradigmas.zeta.models.Element;
import org.unlam.paradigmas.zeta.models.Library;
import org.unlam.paradigmas.zeta.models.MultiRecipe;
import org.unlam.paradigmas.zeta.models.Recipe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElementsQueryTest {
    Query<MultiRecipe> query = new ElementsQuery();

    @Test
    void validateMultiQuery() {

        var l = List.of(
            new Library(
                "base",
                List.of(
                    new Recipe(
                        new Element("D"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("H")
                        )
                    ),
                    new Recipe(
                        new Element("C"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("H")
                        )
                    )
                )
            ),
            new Library(
                "alt",
                List.of(
                    new Recipe(
                        new Element("C"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("H")
                        )
                    )
                )
            )
        );

        MultiRecipe mr = query.run(new Element("C"), l);
        assertEquals(2, mr.libraries.size());
    }

    @Test
    void validateMultiQueryWithNoRecipe() {

        List<Library> l = List.of();

        MultiRecipe mr = query.run(new Element("C"), l);
        assertEquals(0, mr.libraries.size());
    }

    @Test
    void validateNoRecipeInLibrary() {
        var l = List.of(
            new Library(
                "base",
                List.of(
                )
            )
        );

        MultiRecipe mr = query.run(new Element("C"), l);
        assertEquals(0, mr.libraries.size());
    }

    @Test
    void validateNoOriginTableCraft() {
        var l = List.of(
            new Library(
                "",
                List.of(
                    new Recipe(
                        new Element("C"),
                        15,
                        List.of(
                            new Element("H"),
                            new Element("H"),
                            new Element("H")
                        )
                    )
                )
            )
        );

        MultiRecipe mr = query.run(new Element("C"), l);
        assertEquals(1, mr.libraries.size());
        assertEquals("", mr.libraries.get(0).getKey());
    }

    @Test
    void validateNoIngredientsInRecipe() {
        var l = List.of(
            new Library(
                "base",
                List.of(
                    new Recipe(
                        new Element("C"),
                        15,
                        List.of()
                    )
                )
            )
        );

        MultiRecipe mr = query.run(new Element("C"), l);
        assertEquals(1, mr.libraries.size());
        assertEquals("base", mr.libraries.get(0).getKey());
    }
}
