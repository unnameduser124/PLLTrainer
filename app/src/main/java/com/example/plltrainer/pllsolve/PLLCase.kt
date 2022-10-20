package com.example.plltrainer.pllsolve

import com.example.plltrainer.R

enum class PLLCase(val setup: String) {
    Gaperm("R' U' R U D' R2 U R' U R U' R U' R2 D U'"),
    Gbperm("R2 U R' U R' U' R U' R2 U' D R' U R D' U"),
    Gcperm("R U R' U' D R2 U' R U' R' U R' U R2 D' U"),
    Gdperm("R2 U' R U' R U R' U R2 U D' R U' R' D U'"),
    Tperm("R U R' U' R' F R2 U' R' U' R U R' F'"),
    Aaperm("R B' R F2 R' B R F2 R2"),
    Abperm("R' F R' B2 R F' R' B2 R2"),
    Vperm("R' U R' U' B' R' B2 U' B' U B' R B R"),
    Hperm("M2 U' M2 U2 M2 U' M2"),
    Uabperm("R' U R' U' R' U' R' U R U R2"),
    Ubbperm("R U R' U R' U' R2 U' R' U R' U R U2"),
    Raperm("R U2 R' U2 R B' R' U' R U R B R2 U"),
    Rbperm("R' U2 R U2 R' F R U R' U' R' F' R2 U'"),
    Fperm("R' U' F' R U R' U' R' F R2 U' R' U' R U R' U R"),
    Yperm("F R U' R' U' R U R' F' R U R' U' R' F R F'"),
    Naperm("F' R U R' U' R' F R2 F U' R' U' R U F' R'"),
    Nbperm("B R' U' R U R B' R2 B' U R U R' U' B R"),
    Eperm("R' U L' D2 L U' R L' U R' D2 R U' L"),
    Japerm("L' U2 L U L' U2 R U' L U R'"),
    Jbperm("R U2 R' U' R U2 L' U R' U' L"),
    Zperm("M U' M2 U' M2 U' M U2 M2"),
    Error("No case")
}