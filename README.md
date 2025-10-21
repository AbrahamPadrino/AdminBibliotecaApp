# 📗 AdminBibliotecaApp

Aplicación Android desarrollada con **Kotlin** y **Jetpack Compose** para gestionar el control de libros en una biblioteca.
---

## 📑 Requisitos

- Android Studio Giraffe (o superior)
- Xampp v8.0.30
- Node.js v20.14.0
- Visual Studio Code
- JDK 17+
- Android SDK 34+
---

## 🚀 Instalación y ejecución
-    🔳 **Backend**

1. Descarga el archivo *biblioteca_pruebas.sql*. Posteriormente Importalo desde un nuevo proyecto de **Xampp**.
    ```bash
   https://drive.google.com/file/d/1_0J6UjdFOFPoGozTDevYgKzNVXMmLcYE/view?usp=drive_link
   ```
2. Descarga el archivo comprimido: *biblioteca_api.rar* el cual contiene el proyecto de Node.js con Express.
    ```bash
   https://drive.google.com/file/d/1a4pbrCaW4id47mIaE3ynBFyERvsvdljD/view?usp=sharing
   ```
3. Luego de descomprimir el archivo, abrir la carpeta desde VScode para visualizar y ejecutar el proyecto.

   Para poder lanzar y ejecutar el servidor:

    - Abrir la terminal desde VScode y ejecuta el comando: *npm install* el cual se encarga de descargar todas las dependencias necesarias para ejecutar el servidor.
    - Para arrancar el servidor ejecuta: *npm run dev* el cual hace referencia a ejecutar el script declarado en archivo *package.json*
    - Finalmente para comprobar que el servidor ha sido iniciado exitosamente: ir al navegador web y colocar la siguiente ruta: *http://localhost:3000* el cual apunta al puerto en el que se encuentra corriendo el servidor.

- 🔲 **Frontend**

1. Clona este repositorio:
   ```bash
   git clone https://github.com/AbrahamPadrino/AdminBibliotecaApp
   ```

2. Abre el proyecto en **Android Studio**.

3. Sincroniza las dependencias de **Gradle**.

4. Ejecuta el proyecto en un emulador o dispositivo físico.
---

## 🧱 Arquitectura
El proyecto sigue el patrón **MVVM (Model-View-ViewModel)**, separando claramente las capas de datos, lógica y presentación para un desarrollo limpio y escalable.

- **Model:** Gestión de datos remotos recibidos desde el servidor (Retrofit).
- **ViewModel:** Controla el flujo de datos y comunica la UI con la lógica de negocio.
- **View (Compose):** Interfaz moderna y declarativa para una experiencia de usuario fluida.
---

## 🧩 Tecnologías utilizadas
- 🧠 **Lenguaje:** Kotlin
- 🎨 **UI:** Jetpack Compose
- 🗄️ **Base de datos:** Retrofit (Remota)
- ↕️ **Backend:** NodeJS (Express)

- 🧱 **Arquitectura:** MVVM
---
