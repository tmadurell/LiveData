package com.example.livedata;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Pokemon {

    interface PokemonListener {
        void cuandoEvolucione(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> evolucionando;

    //Elemento modificado
    void iniciarEntrenamiento(PokemonListener pokemonListener) {
        if (evolucionando == null || evolucionando.isCancelled()) {
            evolucionando = scheduler.scheduleAtFixedRate(new Runnable() {
                int evolucion;
                int repeticiones = -1;

                @Override
                public void run() {
                    if (repeticiones < 0) {
                        repeticiones = random.nextInt(3) + 2;
                        if (evolucion == 4) {
                            evolucion = 1;
                        } else {
                            evolucion++;
                        }
                    }

                    pokemonListener.cuandoEvolucione("EJERCICIO" + evolucion + ":" + (repeticiones == 0 ? "EVOLUCION" : repeticiones));

                    if (evolucion == 3) {
                        pokemonListener.cuandoEvolucione("EJERCICIO" + evolucion + ":" + (repeticiones == 0 ? "VACIO" : repeticiones));
                    } else if (evolucion == 4){
                        pokemonListener.cuandoEvolucione("EJERCICIO" + evolucion + ":" + (repeticiones == 0 ? "BONUS" : repeticiones));
                    }
                    repeticiones--;
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararEvolucion() {
        if (evolucionando != null) {
            evolucionando.cancel(true);
        }
    }

    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarEntrenamiento(new PokemonListener() {
                @Override
                public void cuandoEvolucione(String evolucion_orden) {
                    postValue(evolucion_orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararEvolucion();
        }
    };

}
