package com.challenge.literAlura.model;

public enum Idioma {
    en("[en]", "Ingles"),
    es("[es]", "Español"),
    fr("[fr]", "Frances"),
    pt("[pt]", "Portugues"),
    it("[it]", "Italiano"),
    ja("[ja]", "Japones"),
    la("[la]", "Latin");

    private String datoGutendex;
    private String datoEspanol;

    Idioma(String datoGutendex, String datoEspanol){
        this.datoGutendex = datoGutendex;
        this.datoEspanol = datoEspanol;
    }

    public static Idioma fromString(String text){
        for(Idioma idioma : Idioma.values()){
            if(idioma.datoGutendex.equalsIgnoreCase(text)){
                return idioma;
            }

        }
        throw new IllegalArgumentException("No se encontro el idioma: " + text);
    }



    public String getIdiomaGutendex() {
        return datoGutendex;
    }

    public String getIdiomaEspanol() { return datoEspanol; }
}
