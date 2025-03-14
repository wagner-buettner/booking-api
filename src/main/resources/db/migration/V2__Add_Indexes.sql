-- Create index for faster lookup of available slots
CREATE INDEX idx_slots_start_date ON slots(start_date);
CREATE INDEX idx_slots_sales_manager ON slots(sales_manager_id);
CREATE INDEX idx_slots_start_end ON slots(start_date, end_date);

-- Create index for filtering sales managers efficiently
CREATE INDEX idx_sales_manager_languages ON sales_managers USING gin (languages);
CREATE INDEX idx_sales_manager_products ON sales_managers USING gin (products);
CREATE INDEX idx_sales_manager_ratings ON sales_managers USING gin (customer_ratings);

CREATE INDEX idx_slots_start_date_booked ON slots(start_date) WHERE booked = FALSE;
CREATE INDEX idx_slots_sales_manager_conflicts ON slots(sales_manager_id, start_date, end_date, booked);
CREATE INDEX idx_sales_manager_fast_filtering ON sales_managers USING gin (languages, products, customer_ratings);
