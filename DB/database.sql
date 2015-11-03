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
    -- UNIQUE (nombre) ON CONFLICT IGNORE
);
CREATE TABLE "contexto" (
    "id" INTEGER PRIMARY KEY NOT NULL,
    "nombre" TEXT UNIQUE NOT NULL,    
    "descripcion" TEXT
    -- UNIQUE (nombre) ON CONFLICT IGNORE -- En el caso de usar esta constraint quitar el UNIQUE que acompaña a la declaracion de "nombre" (dos lineas mas arriba)
);
CREATE TABLE "categoria" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "nombre" TEXT UNIQUE NOT NULL,
    "descripcion" TEXT
    -- UNIQUE (nombre) ON CONFLICT IGNORE
);
CREATE TABLE "notificacion" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "text" TEXT NOT NULL,
    "fechaEnvio" INTEGER,
    "fechaRecepcion" INTEGER,
    "pacienteID" INTEGER NOT NULL,
    "contextoID" INTEGER NOT NULL
);
CREATE TABLE "etiqueta_notificacion" (
	"id" INTEGER PRIMARY KEY NOT NULL,
    "etiquetaID" INTEGER NOT NULL,
    "notificacionID" INTEGER NOT NULL,
    UNIQUE (etiquetaID, notificacionID) ON CONFLICT IGNORE,
    FOREIGN KEY(etiquetaID) REFERENCES etiqueta(id) ON DELETE CASCADE,
	FOREIGN KEY(notificacionID) REFERENCES Notificacion(id) ON DELETE CASCADE
);
CREATE TABLE "categoria_contexto" (
	"id" INTEGER PRIMARY KEY NOT NULL,
	"categoriaID" INTEGER NOT NULL,
    "contextoID" INTEGER NOT NULL,
    UNIQUE (categoriaID, contextoID) ON CONFLICT IGNORE,
	FOREIGN KEY(categoriaID) REFERENCES categoria(id) ON DELETE CASCADE,
	FOREIGN KEY(contextoID) REFERENCES contexto(id) ON DELETE CASCADE
);
