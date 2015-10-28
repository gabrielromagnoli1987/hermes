CREATE TABLE "paciente" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "nombre" TEXT NOT NULL,
    "apellido" TEXT NOT NULL
);
CREATE TABLE sqlite_sequence(name,seq);
CREATE TABLE "etiqueta" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "nombre" TEXT UNIQUE NOT NULL,
    "descripcion" TEXT
);
CREATE TABLE "contexto" (
    "id" INTEGER PRIMARY KEY NOT NULL,
    "nombre" TEXT UNIQUE NOT NULL,
    "descripcion" TEXT
);
CREATE TABLE "categoria" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "nombre" TEXT UNIQUE NOT NULL,
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
	"id" INTEGER PRIMARY KEY NOT NULL,
    "etiquetaID" INTEGER NOT NULL,
    "notificacionID" INTEGER NOT NULL,
    FOREIGN KEY(etiquetaID) REFERENCES etiqueta(id) ON DELETE CASCADE,
	FOREIGN KEY(notificacionID) REFERENCES Notificacion(id) ON DELETE CASCADE
);
CREATE TABLE "categoria_contexto" (
	"id" INTEGER PRIMARY KEY NOT NULL,
	"categoriaID" INTEGER NOT NULL,
    "contextoID" INTEGER NOT NULL,
	FOREIGN KEY(categoriaID) REFERENCES categoria(id) ON DELETE CASCADE,
	FOREIGN KEY(contextoID) REFERENCES contexto(id) ON DELETE CASCADE
);
