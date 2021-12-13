package com.example.livedata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.livedata.databinding.FragmentPokemonBinding;


public class PokemonFragment extends Fragment {

    private FragmentPokemonBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentPokemonBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PokemonViewModel pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        pokemonViewModel.obtenerEvolucion().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer pokemon) {
                Glide.with(PokemonFragment.this).load(pokemon).into(binding.pokemon);
            }
        });

        pokemonViewModel.obtenerRepeticion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String evolucion) {
                if(evolucion.equals("EVOLUCION")){
                    binding.cambio.setVisibility(View.VISIBLE);
                } else {
                    binding.cambio.setVisibility(View.GONE);
                }
                //Sirve para que aparezca la cuenta regresiva a 0
                //binding.evolucion.setText(evolucion);
            }
        });
        //Nuevo elemento añadido para no ser lo mismo
        pokemonViewModel.obtenerRepeticion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String bonus) {
                if(bonus.equals("BONUS")){
                    binding.bonus.setVisibility(View.VISIBLE);
                } else {
                    binding.bonus.setVisibility(View.GONE);
                }
                //Sirve para que aparezca la cuenta regresiva a 0
                //binding.repeticion.setText(bonus);
            }
        });
        //Nuevo elemento añadido para no ser lo mismo
        pokemonViewModel.obtenerRepeticion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String bonus) {
                if(bonus.equals("VACIO")){
                    binding.bonus.setVisibility(View.VISIBLE);
                } else {
                    binding.bonus.setVisibility(View.GONE);
                }
                //Sirve para que aparezca la cuenta regresiva a 0
                //binding.repeticion.setText(bonus);
            }
        });
    }
}