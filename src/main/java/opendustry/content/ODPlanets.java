package opendustry.content;

import arc.graphics.Color;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.graphics.g3d.SunMesh;
import mindustry.maps.generators.BlankPlanetGenerator;
import mindustry.type.Planet;
import opendustry.graphics.g3d.DysonRingMesh;


public class ODPlanets {
    public static Planet milkyway, kerbal, mun, minmus;

    public static void load() {
        milkyway = new Planet("milkyway", null, 4f) {{
            bloom = true;
            accessible = false;
            solarSystem = this;

            meshLoader = () -> new SunMesh(
                    this, 4,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("#ff7a38"),
                    Color.valueOf("#ff9638"),
                    Color.valueOf("#ffc64c"),
                    Color.valueOf("#ffc64c"),
                    Color.valueOf("#ffe371"),
                    Color.valueOf("#f4ee8e")
            );
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(
                            this,
                            11,
                            0.15f,
                            0.13f,
                            5,
                            Color.valueOf("#78d4ff").mul(0.9f).a(0.75f),
                            2,
                            0.45f,
                            0.9f,
                            0.38f
                    ),
                    new HexSkyMesh(
                            this,
                            1,
                            0.6f,
                            0.16f,
                            5,
                            Color.white.cpy().lerp(Color.valueOf("#78d4ff"), 0.55f).a(0.75f),
                            2,
                            0.45f,
                            1f,
                            0.41f
                    ),
                    new DysonRingMesh(
                            this,
                            2.300f,
                            0.28f,
                            729,
                            Pal.darkMetal,
                            Pal.darkerMetal
                    ),
                    new DysonRingMesh(
                            this,
                            2.500f,
                            0.28f,
                            2941,
                            Pal.darkMetal,
                            Pal.darkerMetal
                    ),
                    new DysonRingMesh(
                            this,
                            2.700f,
                            0.28f,
                            3834,
                            Pal.darkMetal,
                            Pal.darkerMetal
                    ),
                    new DysonRingMesh(
                            this,
                            2.305f,
                            0.19f,
                            729,
                            Pal.sapBullet.cpy().mul(1.075f).lerp(Color.white, 0.075f),
                            Pal.sapBullet.cpy().mul(1.075f).lerp(Color.white, 0.075f)
                    ),
                    new DysonRingMesh(
                            this,
                            2.505f,
                            0.19f,
                            2941,
                            Pal.sapBullet.cpy().mul(1.075f).lerp(Color.white, 0.075f),
                            Pal.sapBullet.cpy().mul(1.075f).lerp(Color.white, 0.075f)
                    ),
                    new DysonRingMesh(
                            this,
                            2.705f,
                            0.19f,
                            3834,
                            Pal.sapBullet.cpy().mul(1.075f).lerp(Color.white, 0.075f),
                            Pal.sapBullet.cpy().mul(1.075f).lerp(Color.white, 0.075f)
                    )
            );
        }};

        kerbal = new Planet("kerbal", milkyway, 1f, 4) {{
            accessible = true;
            alwaysUnlocked = true;
            startSector = 0;

            solarSystem = milkyway;
            orbitRadius = 40f;
            rotateTime = 23f * 60f;

            generator = new BlankPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(
                            this,
                            11,
                            0.15f,
                            0.13f,
                            5,
                            Color.valueOf("#78d4ff").mul(0.9f).a(0.75f),
                            2,
                            0.45f,
                            0.9f,
                            0.38f
                    ),
                    new HexSkyMesh(
                            this,
                            1,
                            0.6f,
                            0.16f,
                            5,
                            Color.white.cpy().lerp(Color.valueOf("#78d4ff"), 0.55f).a(0.75f),
                            2,
                            0.45f,
                            1f,
                            0.41f
                    )
            );

            iconColor = Color.valueOf("#358f54");
            atmosphereColor = Color.valueOf("#4dc6ff");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;


        }};
    }
}
