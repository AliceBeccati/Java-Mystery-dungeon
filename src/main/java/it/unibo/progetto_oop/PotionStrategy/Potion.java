package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.Combat.CombatModel;

public class Potion {
    private final String name;
    private final String description;
    private int quantity;
    private final PotionEffectStrategy strategy;

    /**
     * 
     * @param name Nome che descrive la pozione
     * @param description Descrizione di funzione della pozione
     * @param quantity Quantità nell'inventario
     * @param strategy Istanza di Pozione necessaria per chiamare i metodi necessari
     */

    public Potion(String name, String description, int quantity, PotionEffectStrategy strategy){
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.strategy = strategy;
    }

    /**
     * 
     * @param user Istanza di CombatModel da passare a apllyEffect per chiamare i metodi necessari nelle singole Pozioni
     */

    public void use(CombatModel user){
        if (this.quantity > 0){
            this.strategy.apllyEffect(user);
            this.quantity--;
        }
        else{
            System.out.println("Non hai abbastanza pozioni " + this.name);
        }
    }

    /**
     * 
     * @return Stringa che descrive quantità di una singola pozione che hai
     */

    public String getInfo(){
        return "Hai: " + this.quantity + "pozioni " + this.name + "la cui descrizione è " + this.description;
    }

}
