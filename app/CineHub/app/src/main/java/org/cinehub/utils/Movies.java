package org.cinehub.utils;

import java.util.ArrayList;
import java.util.List;

public class Movies {
    public static List<MovieTest> getMovies() {
        List<MovieTest> movies = new ArrayList<>();

        movies.add(new MovieTest("Moon", "Un astronauta que trabaja en la luna tiene un accidente y comienza a cuestionar su propia identidad en un ambiente aislado y solitario.", "2009-06-12"));
        movies.add(new MovieTest("Memento", "Un hombre sufre de pérdida de memoria a corto plazo y utiliza tatuajes y notas para investigar y vengar la muerte de su esposa.", "2000-10-11"));
        movies.add(new MovieTest("Her", "Un escritor solitario desarrolla una relación romántica con su sistema operativo de inteligencia artificial en un futuro cercano.", "2013-11-20"));
        movies.add(new MovieTest("Election", "Una estudiante ambiciosa y un profesor descontento se enfrentan en una batalla por la presidencia del consejo estudiantil.", "1999-05-07"));
        movies.add(new MovieTest("Rush", "La rivalidad entre los pilotos de Fórmula 1 James Hunt y Niki Lauda llega a un punto crítico durante la temporada de 1976.", "2013-09-02"));
        movies.add(new MovieTest("Whiplash", "Un joven baterista en busca de perfección es presionado hasta el límite por su exigente instructor en una escuela de música.", "2014-01-16"));
        movies.add(new MovieTest("Drive", "Un conductor de Hollywood especializado en huidas es contratado para ayudar en un robo que sale mal y termina en una peligrosa persecución.", "2011-09-16"));

        return movies;
    }
}
