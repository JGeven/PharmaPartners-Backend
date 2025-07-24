CREATE TABLE currency (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          ticker VARCHAR(10),
                          name VARCHAR(100),
                          number_of_coins BIGINT,
                          market_cap BIGINT
);
