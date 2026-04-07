# MiniCraft 2D (JavaFX)

## Descrição

Este projeto consiste no desenvolvimento de uma versão simplificada do jogo Minecraft, utilizando Java e JavaFX. A abordagem inicial é em 2D, com o objetivo de compreender os conceitos fundamentais de desenvolvimento de jogos antes de evoluir para uma versão 3D com OpenGL.

---

## Objetivos

* Aprender os fundamentos de game development em Java
* Implementar um sistema de mundo baseado em blocos (voxels 2D)
* Compreender renderização, física básica e interação do jogador
* Evoluir progressivamente para uma arquitetura mais próxima de uma game engine

---

## Funcionalidades atuais

* Renderização de mundo em grade (blocos)
* Sistema de câmera que segue o jogador
* Movimento do jogador com física básica (gravidade e salto)
* Colisão com blocos
* Colocação e remoção de blocos com o mouse
* Diferentes tipos de blocos (relva, terra, pedra) com texturas
* Geração procedural de terreno (colinas e variações)
* Sistema de chunks para simular mundo infinito

---

## Tecnologias utilizadas

* Java
* JavaFX
* Estruturas de dados (matrizes e mapas)
* Conceitos básicos de geração procedural

---

## Estrutura do projeto

* `Main` – ponto de entrada da aplicação
* `Game` – loop principal, renderização e input
* `World` – gestão do mundo e dos blocos
* `Chunk` – divisão do mundo em partes dinâmicas
* `Player` – lógica do jogador (movimento e colisão)
* `Block / BlockType` – definição de blocos e texturas

---

## Próximos passos

* Implementação de inventário
* Sistema de mineração (tempo de quebra de blocos)
* Interface (HUD)
* Melhoria da geração procedural
* Transição para 3D com OpenGL (LWJGL)

---
## Objetivo futuro

Transformar este projeto em uma base sólida para desenvolvimento de jogos, evoluindo de um protótipo 2D para uma versão mais avançada em 3D.
