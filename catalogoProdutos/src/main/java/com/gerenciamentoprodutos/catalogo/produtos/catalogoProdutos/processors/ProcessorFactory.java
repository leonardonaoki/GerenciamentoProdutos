package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors.impl.CsvProcessor;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors.impl.JsonProcessor;

public class ProcessorFactory {

    public static IProcessor getProcessor(String fileExtension)
    {
        switch (fileExtension){
//            case "CSV":
//                return new CsvProcessor();
            case "JSON":
                return new JsonProcessor();
            case "XML":
                return new JsonProcessor();
            default:
                return null;
        }
    }

}
