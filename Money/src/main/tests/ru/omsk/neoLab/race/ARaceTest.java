package ru.omsk.neoLab.race;

import org.junit.Assert;
import org.junit.Test;

public class ARaceTest {

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

    @Test
    public void getCountUnitAmphibia() {

        ARace race = new Amphibia();

        String nameRace = race.getNameRace();

        Assert.assertEquals(nameRace, "Amphibia");
    }

    @Test
    public void getCountUnitDwarfs() {

        ARace race = new Amphibia();

        String nameRace = race.getNameRace();

        Assert.assertEquals(nameRace, "Amphibia");
    }

    @Test
    public void getCountUnitElfs() {

        ARace race = new Amphibia();

        String nameRace = race.getNameRace();

        Assert.assertEquals(nameRace, "Amphibia");

    }

    @Test
    public void getCountUnitMushrooms() {

        ARace race = new Amphibia();

        String nameRace = race.getNameRace();

        Assert.assertEquals(nameRace, "Amphibia");

    }

    @Test
    public void getCountUnitOrcs() {

        ARace race = new Amphibia();

        String nameRace = race.getNameRace();

        Assert.assertEquals(nameRace, "Amphibia");

    }

    @Test
    public void getCountUnitUndead() {

        ARace race = new Amphibia();

        String nameRace = race.getNameRace();

        Assert.assertEquals(nameRace, "Amphibia");

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