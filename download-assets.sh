#!/bin/bash

mkdir -p uploads

generate_uuid() {
    if [[ "$OSTYPE" == "darwin"* ]]; then
        uuidgen | tr '[:upper:]' '[:lower:]'
    else
        cat /proc/sys/kernel/random/uuid
    fi
}

# Download sample business images with UUID filenames
echo "Downloading business images..."

# Tech hub image
UUID1=$(generate_uuid)
curl "https://images.unsplash.com/photo-1497366216548-37526070297c?w=500" -o "uploads/$UUID1.jpg"
echo "Tech hub image saved as: $UUID1.jpg"

# Fitness image
UUID2=$(generate_uuid)
curl "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=500" -o "uploads/$UUID2.jpg"
echo "Fitness image saved as: $UUID2.jpg"

# Pet services image
UUID3=$(generate_uuid)
curl "https://images.unsplash.com/photo-1527526029430-319f10814151?w=500" -o "uploads/$UUID3.jpg"
echo "Pet services image saved as: $UUID3.jpg"

# Bank image
UUID4=$(generate_uuid)
curl "https://images.unsplash.com/photo-1541354329998-f4d9a9f9297f?w=500" -o "uploads/$UUID4.jpg"
echo "Bank image saved as: $UUID4.jpg"

# Cinema image
UUID5=$(generate_uuid)
curl "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=500" -o "uploads/$UUID5.jpg"
echo "Cinema image saved as: $UUID5.jpg"

# Create a SQL file with the UUIDs
echo "Updating data.sql with new UUIDs..."
cat > src/main/resources/data.sql << EOF
-- Insert Categories
INSERT INTO category (name)
VALUES 
    ('Shopping'),
    ('Restaurant'),
    ('Entertainment'),
    ('Hotel'),
    ('Education'),
    ('Health'),
    ('Sports'),
    ('Beauty'),
    ('Automotive'),
    ('Banking'),
    ('Fitness'),
    ('Technology'),
    ('Real Estate'),
    ('Travel'),
    ('Groceries'),
    ('Pharmacy'),
    ('Library'),
    ('Cinema'),
    ('Laundry'),
    ('Pet Services');

-- Insert Businesses
INSERT INTO business (name, category_id, location, about, contact, website_url, image_url) 
VALUES
    -- Technology Business
    ('Roko Tech Hub', 12, 'Bukoto', 'Leading technology innovation center and co-working space',
     '+256-757-123456', 'https://rokotechhub.ug',
     '/images/$UUID1.jpg'),

    -- Fitness Center
    ('Fitness Plus', 11, 'Kololo', 'State-of-the-art gym with professional trainers and modern equipment',
     '+256-701-234567', 'https://fitnessplus.ug',
     '/images/$UUID2.jpg'),

    -- Pet Services
    ('Happy Paws Clinic', 20, 'Muyenga', 'Professional veterinary services and pet grooming',
     '+256-772-345678', 'https://happypaws.ug',
     '/images/$UUID3.jpg'),

    -- Banking
    ('Metro Bank', 10, 'Downtown Kampala', '24/7 banking services with modern digital solutions',
     '+256-417-456789', 'https://metrobank.ug',
     '/images/$UUID4.jpg'),

    -- Cinema
    ('Century Cinemax', 18, 'Acacia Mall', 'Premium movie experience with state-of-the-art sound systems',
     '+256-789-567890', 'https://centurycinemax.ug',
     '/images/$UUID5.jpg');
EOF

echo "Download complete!"
echo "Images and icons have been saved with UUIDs and data.sql has been updated."
