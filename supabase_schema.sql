-- Create the 'shops' table
CREATE TABLE shops (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    village TEXT NOT NULL,
    lat FLOAT8 NOT NULL,
    lng FLOAT8 NOT NULL,
    pharmacist_name TEXT NOT NULL,
    phone TEXT NOT NULL,
    distance_km FLOAT8 NOT NULL
);

-- Create the 'medicines' table
CREATE TABLE medicines (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    generic_name TEXT NOT NULL,
    category TEXT NOT NULL,
    price FLOAT8 NOT NULL,
    stock INT4 NOT NULL,
    expiry_date INT8 NOT NULL, -- Stored as epoch milliseconds
    shop_id TEXT NOT NULL REFERENCES shops(id) ON DELETE CASCADE,
    unit TEXT NOT NULL
);

-- Enable Row Level Security (RLS)
-- If you want anyone to be able to read/write for development, you can use these policies:
ALTER TABLE shops ENABLE ROW LEVEL SECURITY;
ALTER TABLE medicines ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Enable read access for all users" ON shops FOR SELECT USING (true);
CREATE POLICY "Enable insert access for all users" ON shops FOR INSERT WITH CHECK (true);
CREATE POLICY "Enable update access for all users" ON shops FOR UPDATE USING (true);

CREATE POLICY "Enable read access for all users" ON medicines FOR SELECT USING (true);
CREATE POLICY "Enable insert access for all users" ON medicines FOR INSERT WITH CHECK (true);
CREATE POLICY "Enable update access for all users" ON medicines FOR UPDATE USING (true);
