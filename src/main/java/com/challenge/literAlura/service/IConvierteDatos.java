package com.challenge.literAlura.service;

import com.challenge.literAlura.model.DatosAutor;
import com.challenge.literAlura.model.DatosLibro;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);

}
