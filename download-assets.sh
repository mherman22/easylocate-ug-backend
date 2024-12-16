#!/bin/bash

mkdir -p icons
mkdir -p uploads/images

generate_uuid() {
    if [[ "$OSTYPE" == "darwin"* ]]; then
        uuidgen | tr '[:upper:]' '[:lower:]'
    else
        cat /proc/sys/kernel/random/uuid
    fi
}

# Download Material Design Icons with UUID filenames
echo "Downloading icons..."

# Shopping icon
ICON_UUID1=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/shopping_cart/v12/24px.svg" -o "icons/$ICON_UUID1.svg"
echo "Shopping icon saved as: $ICON_UUID1.svg"

# Restaurant icon
ICON_UUID2=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/restaurant/v12/24px.svg" -o "icons/$ICON_UUID2.svg"
echo "Restaurant icon saved as: $ICON_UUID2.svg"

# Entertainment icon
ICON_UUID3=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/local_activity/v12/24px.svg" -o "icons/$ICON_UUID3.svg"
echo "Entertainment icon saved as: $ICON_UUID3.svg"

# Hotel icon
ICON_UUID4=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/hotel/v12/24px.svg" -o "icons/$ICON_UUID4.svg"
echo "Hotel icon saved as: $ICON_UUID4.svg"

# Education icon
ICON_UUID5=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/school/v12/24px.svg" -o "icons/$ICON_UUID5.svg"
echo "Education icon saved as: $ICON_UUID5.svg"

# Health icon
ICON_UUID6=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/local_hospital/v12/24px.svg" -o "icons/$ICON_UUID6.svg"
echo "Health icon saved as: $ICON_UUID6.svg"

# Sports icon
ICON_UUID7=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/sports_soccer/v12/24px.svg" -o "icons/$ICON_UUID7.svg"
echo "Sports icon saved as: $ICON_UUID7.svg"

# Beauty icon
ICON_UUID8=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/spa/v12/24px.svg" -o "icons/$ICON_UUID8.svg"
echo "Beauty icon saved as: $ICON_UUID8.svg"

# Automotive icon
ICON_UUID9=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/directions_car/v12/24px.svg" -o "icons/$ICON_UUID9.svg"
echo "Automotive icon saved as: $ICON_UUID9.svg"

# Banking icon
ICON_UUID10=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/account_balance/v12/24px.svg" -o "icons/$ICON_UUID10.svg"
echo "Banking icon saved as: $ICON_UUID10.svg"

# Fitness icon
ICON_UUID11=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/fitness_center/v12/24px.svg" -o "icons/$ICON_UUID11.svg"
echo "Fitness icon saved as: $ICON_UUID11.svg"

# Technology icon
ICON_UUID12=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/computer/v12/24px.svg" -o "icons/$ICON_UUID12.svg"
echo "Technology icon saved as: $ICON_UUID12.svg"

# Real Estate icon
ICON_UUID13=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/apartment/v12/24px.svg" -o "icons/$ICON_UUID13.svg"
echo "Real Estate icon saved as: $ICON_UUID13.svg"

# Travel icon
ICON_UUID14=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/flight/v12/24px.svg" -o "icons/$ICON_UUID14.svg"
echo "Travel icon saved as: $ICON_UUID14.svg"

# Groceries icon
ICON_UUID15=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/local_grocery_store/v12/24px.svg" -o "icons/$ICON_UUID15.svg"
echo "Groceries icon saved as: $ICON_UUID15.svg"

# Pharmacy icon
ICON_UUID16=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/local_pharmacy/v12/24px.svg" -o "icons/$ICON_UUID16.svg"
echo "Pharmacy icon saved as: $ICON_UUID16.svg"

# Library icon
ICON_UUID17=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/local_library/v12/24px.svg" -o "icons/$ICON_UUID17.svg"
echo "Library icon saved as: $ICON_UUID17.svg"

# Cinema icon
ICON_UUID18=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/local_movies/v12/24px.svg" -o "icons/$ICON_UUID18.svg"
echo "Cinema icon saved as: $ICON_UUID18.svg"

# Laundry icon
ICON_UUID19=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/local_laundry_service/v12/24px.svg" -o "icons/$ICON_UUID19.svg"
echo "Laundry icon saved as: $ICON_UUID19.svg"

# Pet Services icon
ICON_UUID20=$(generate_uuid)
curl "https://fonts.gstatic.com/s/i/materialicons/pets/v12/24px.svg" -o "icons/$ICON_UUID20.svg"
echo "Pet Services icon saved as: $ICON_UUID20.svg"

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
INSERT INTO category (name, icon) 
VALUES 
    ('Shopping', '/icons/$ICON_UUID1.svg'),
    ('Restaurant', '/icons/$ICON_UUID2.svg'),
    ('Entertainment', '/icons/$ICON_UUID3.svg'),
    ('Hotel', '/icons/$ICON_UUID4.svg'),
    ('Education', '/icons/$ICON_UUID5.svg'),
    ('Health', '/icons/$ICON_UUID6.svg'),
    ('Sports', '/icons/$ICON_UUID7.svg'),
    ('Beauty', '/icons/$ICON_UUID8.svg'),
    ('Automotive', '/icons/$ICON_UUID9.svg'),
    ('Banking', '/icons/$ICON_UUID10.svg'),
    ('Fitness', '/icons/$ICON_UUID11.svg'),
    ('Technology', '/icons/$ICON_UUID12.svg'),
    ('Real Estate', '/icons/$ICON_UUID13.svg'),
    ('Travel', '/icons/$ICON_UUID14.svg'),
    ('Groceries', '/icons/$ICON_UUID15.svg'),
    ('Pharmacy', '/icons/$ICON_UUID16.svg'),
    ('Library', '/icons/$ICON_UUID17.svg'),
    ('Cinema', '/icons/$ICON_UUID18.svg'),
    ('Laundry', '/icons/$ICON_UUID19.svg'),
    ('Pet Services', '/icons/$ICON_UUID20.svg');

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
