-- Activar extensión para UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tabla de franquicias
CREATE TABLE IF NOT EXISTS franchise (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de sucursales
CREATE TABLE IF NOT EXISTS branch (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    franchise_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id) REFERENCES franchise(id) ON DELETE CASCADE
);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS product (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    stock INTEGER NOT NULL CHECK (stock >= 0),
    branch_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_branch FOREIGN KEY (branch_id) REFERENCES branch(id) ON DELETE CASCADE
);

-- Índices para relaciones
CREATE INDEX IF NOT EXISTS idx_branch_franchise_id ON branch(franchise_id);
CREATE INDEX IF NOT EXISTS idx_product_branch_id ON product(branch_id);
