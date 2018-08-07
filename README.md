# batallaNaval
Test realizado por Christian Bonacic-Kresic, para la empresa EXPERTA ART en Java puro: juego Batalla Naval (Agosto 2018)

Consideraciones generales:

1) realicé la aplicación en Java puro, utilizando SWING/AWT. Versión JRE de Java 1.8.0_181
2) para ser accedido desde internet, puede verse desde una página que llame el archivo JNLP de la aplicación
3) las imágenes las tomé de internet y fueron modificadas para su uso en la aplicación
4) no le pude dedicar el tiempo que hubiera querido por obligaciones personales/laborales

Consideraciones particulares:

1) pensé en realizarlo de una manera más original, como matrices de botones y no layers de drag and drop
2) al ser SWING(AWT y utilizar en su gran mayoría las clases nativas, no hubo necesidad de estructuras de herencias entre clases y demás
3) tuve problemas para girar las imágenes al agregar los barcos propios, por lo que no se pueden girar visualmente a vertical lamentablemente. La aclaración importante es que el programa admite a nivel código lo vertical, es solo una cuestión visual. El enemigo inclusive ubica sus barcos random horizontal/vertical y utiliza las mismas funciones
4) utilicé a nivel visual un layered panel, para poder gestionar los tableros, botones y labels. A nivel código, utilicé matrices de strings para la representación de los tableros
5) utilicé solamente cuando me pareció realmente necesario las funcionalidades de Streams y Lambdas
6) traté de reutilizar todas las funciones para ambos bandos
7) por cuestiones de optimización cargué las imágenes a nivel general una sola vez para utilizarlas muchas veces luego
8) comenté las funciones importantes en el código mismo
