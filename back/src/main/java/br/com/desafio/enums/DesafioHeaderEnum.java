package br.com.desafio.enums;

public enum DesafioHeaderEnum {
    PAGINA("page"),
    TAMANHO_PAGINA("size"),
    CAMPO_ORDEM("order"),
    DECRESCENTE("desc");

    private String value;
    
    private DesafioHeaderEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}