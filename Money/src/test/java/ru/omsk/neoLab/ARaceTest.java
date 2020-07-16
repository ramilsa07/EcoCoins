package ru.omsk.neoLab;

import org.junit.Assert;
import org.junit.Test;
import ru.omsk.neoLab.race.*;

public class ARaceTest {

    // Имя расы
    @Test
    public void getNameRaceAmphibia() {
        ARace race = new Amphibia();
        String nameRace = race.getNameRace();
        Assert.assertEquals(nameRace, "Amphibia");
    }

    @Test
    public void getNameRaceDwarfs() {
        ARace race = new Dwarfs();
        String nameRace = race.getNameRace();
        Assert.assertEquals(nameRace, "Dwarfs");
    }

    @Test
    public void getNameRaceElfs() {
        ARace race = new Elfs();
        String nameRace = race.getNameRace();
        Assert.assertEquals(nameRace, "Elfs");
    }

    @Test
    public void getNameRaceMushrooms() {
        ARace race = new Mushrooms();
        String nameRace = race.getNameRace();
        Assert.assertEquals(nameRace, "Mushrooms");
    }

    @Test
    public void getNameRaceOrcs() {
        ARace race = new Orcs();
        String nameRace = race.getNameRace();
        Assert.assertEquals(nameRace, "Orcs");
    }

    @Test
    public void getNameRaceUndead() {
        ARace race = new Undead();
        String nameRace = race.getNameRace();
        Assert.assertEquals(nameRace, "Undead");
    }

    // Число токенов
    @Test
    public void getCountTokensAmphibia() {

        ARace race = new Amphibia();
        int countUnit = race.getCountTokens();
        Assert.assertEquals(countUnit, 6);
    }

    @Test
    public void getCountTokensDwarfs() {

        ARace race = new Dwarfs();
        int countUnit = race.getCountTokens();
        Assert.assertEquals(countUnit, 5);
    }

    @Test
    public void getCountTokensElfs() {

        ARace race = new Elfs();
        int countUnit = race.getCountTokens();
        Assert.assertEquals(countUnit, 6);

    }

    @Test
    public void getCountTokensMushrooms() {

        ARace race = new Mushrooms();
        int countUnit = race.getCountTokens();
        Assert.assertEquals(countUnit, 6);

    }

    @Test
    public void getCountTokensOrcs() {

        ARace race = new Orcs();
        int countUnit = race.getCountTokens();
        Assert.assertEquals(countUnit, 5);

    }

    @Test
    public void getCountTokensUndead() {

        ARace race = new Undead();
        int countUnit = race.getCountTokens();
        Assert.assertEquals(countUnit, 11);

    }

    @Test
    public void toDefend() {
    }

    @Test
    public void getRequirementsForCapture() {

    }

    @Test
    public void toDecline() {
    }
}