CREATE TABLE "paciente" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "nombre" TEXT NOT NULL,
    "apellido" TEXT NOT NULL
);
CREATE TABLE sqlite_sequence(name,seq);
CREATE TABLE "Etiqueta" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "nombre" TEXT NOT NULL,
    "descripcion" TEXT
);
CREATE TABLE "Contexto" (
    "id" INTEGER PRIMARY KEY NOT NULL,
    "nombre" TEXT NOT NULL,
    "descripcion" TEXT
);
CREATE TABLE "categoria" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "nombre" TEXT NOT NULL,
    "descripcion" TEXT
);
CREATE TABLE "notificacion" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "text" TEXT NOT NULL,
    "fechaEnvio" TEXT,
    "fechaRecepcion" TEXT,
    "pacienteID" INTEGER NOT NULL,
    "contextoID" INTEGER NOT NULL
);
CREATE TABLE "etiqueta_notificacion" (
    "etiquetaID" INTEGER NOT NULL,
    "notificacionID" INTEGER NOT NULL
);
CREATE TABLE "categoria_contexto" (
	"id" INTEGER PRIMARY KEY NOT NULL,
	"categoriaID" INTEGER NOT NULL,
    "contextoID" INTEGER NOT NULL,
	FOREIGN KEY(categoriaID) REFERENCES Categoria(id),
	FOREIGN KEY(contextoID) REFERENCES Contexto(id)
);
