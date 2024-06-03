CREATE TABLE Unite (
    id_unite SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Ingredient (
    id_ingredient SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix NUMERIC(10, 2) NOT NULL,
    id_unite INT NOT NULL,
    stock NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (id_unite) REFERENCES Unite(id_unite)
);

CREATE TABLE Menu (
    id_menu SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    prix_vente NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IngredientMenu (
    id_ingredient_menu SERIAL PRIMARY KEY,
    id_menu INT NOT NULL,
    id_ingredient INT NOT NULL,
    quantite_necessaire NUMERIC(10, 2) NOT NULL,
    type VARCHAR(255) NOT NULL,
    id_unite INT NOT NULL,
    date_movement TIMESTAMP NOT NULL,
    FOREIGN KEY (id_menu) REFERENCES Menu(id_menu),
    FOREIGN KEY (id_ingredient) REFERENCES Ingredient(id_ingredient),
    FOREIGN KEY (id_unite) REFERENCES Unite(id_unite)
);

CREATE TABLE Restaurant (
    id_restaurant SERIAL PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    id_ingredient_menu INT,
    FOREIGN KEY (id_ingredient_menu) REFERENCES IngredientMenu(id_ingredient_menu)
);



-- Insérer des enregistrements dans la table Unite
INSERT INTO Unite (name) VALUES ('Kilogramme');
INSERT INTO Unite (name) VALUES ('Litre');
INSERT INTO Unite (name) VALUES ('Pièce');

-- Insérer des enregistrements dans la table Ingredient
INSERT INTO Ingredient (nom, prix, id_unite, stock) VALUES ('Farine', 1.50, 1, 100.00);
INSERT INTO Ingredient (nom, prix, id_unite, stock) VALUES ('Lait', 0.99, 2, 50.00);
INSERT INTO Ingredient (nom, prix, id_unite, stock) VALUES ('Oeuf', 0.20, 3, 200.00);

-- Insérer des enregistrements dans la table Menu
INSERT INTO Menu (name, prix_vente) VALUES ('Crêpe', 5.00);
INSERT INTO Menu (name, prix_vente) VALUES ('Gaufre', 4.00);
INSERT INTO Menu (name, prix_vente) VALUES ('Croissant', 2.50);

-- Insérer des enregistrements dans la table IngredientMenu
INSERT INTO IngredientMenu (id_menu, id_ingredient, quantite_necessaire, type, id_unite, date_movement) VALUES (1, 1, 0.25, 'Ingrédient', 1, '2024-06-01 12:00:00');
INSERT INTO IngredientMenu (id_menu, id_ingredient, quantite_necessaire, type, id_unite, date_movement) VALUES (1, 2, 0.50, 'Ingrédient', 2, '2024-06-01 12:00:00');
INSERT INTO IngredientMenu (id_menu, id_ingredient, quantite_necessaire, type, id_unite, date_movement) VALUES (1, 3, 2.00, 'Ingrédient', 3, '2024-06-01 12:00:00');

-- Insérer des enregistrements dans la table Restaurant
INSERT INTO Restaurant (location, id_ingredient_menu) VALUES ('Paris', 1);
INSERT INTO Restaurant (location, id_ingredient_menu) VALUES ('Lyon', 2);
INSERT INTO Restaurant (location, id_ingredient_menu) VALUES ('Marseille', 3);
