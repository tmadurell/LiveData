package com.example.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


public class PokemonViewModel extends AndroidViewModel {
    Pokemon pokemon;

    LiveData<Integer> pokemonLiveData;
    LiveData<String> mostrarLiveData;

    public PokemonViewModel(@NonNull Application application) {
        super(application);

        pokemon = new Pokemon();

        pokemonLiveData = Transformations.switchMap(pokemon.ordenLiveData, new Function<String, LiveData<Integer>>() {

            String evolucionAnterior;

            //Solo se han cambiado las imagenes drawable
            @Override
            public LiveData<Integer> apply(String evolucion_orden) {

                String evolucion = evolucion_orden.split(":")[0];

                if(!evolucion.equals(evolucionAnterior)){
                    evolucionAnterior = evolucion;
                    int imagen;
                    switch (evolucion) {
                        case "EJERCICIO1":
                        default:
                            imagen = R.drawable.pichu;
                            break;
                        case "EJERCICIO2":
                            imagen = R.drawable.pikachu;
                            break;
                        case "EJERCICIO3":
                            imagen = R.drawable.raichu;
                            break;
                        case "EJERCICIO4":
                            imagen = R.drawable.pikachudetective;
                            break;
                    }

                    return new MutableLiveData<>(imagen);
                }
                return null;
            }
        });

        mostrarLiveData = Transformations.switchMap(pokemon.ordenLiveData, new Function<String, LiveData<String>>() {
            @Override
            public LiveData<String> apply(String evolucion_orden) {
                return new MutableLiveData<>(evolucion_orden.split(":")[1]);
            }
        });
    }

    LiveData<Integer> obtenerEvolucion(){
        return pokemonLiveData;
    }

    LiveData<String> obtenerRepeticion(){
        return mostrarLiveData;
    }
}