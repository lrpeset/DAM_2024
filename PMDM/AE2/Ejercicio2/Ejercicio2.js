import { View, Pressable, ScrollView, Text, StyleSheet, Image, Modal } from "react-native";
import { useState, useEffect } from "react";
import Card from "./Card";

const API_URL = "https://pokeapi.co/api/v2/pokemon";

export default function Ejercicio2() {
  const [pokemons, setPokemons] = useState([]);
  const [page, setPage] = useState(1);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedPokemon, setSelectedPokemon] = useState(null);

  useEffect(() => {
    const fetchPokemons = async () => {
      try {
        const response = await fetch(`${API_URL}?offset=${(page - 1) * 20}&limit=20`);
        const data = await response.json();
        const pokemonsData = await Promise.all(
          data.results.map(async (pokemon) => {
            const pokemonDetail = await fetch(pokemon.url);
            const pokemonData = await pokemonDetail.json();
            return {
              name: pokemon.name,
              image: pokemonData.sprites.front_default,
              details: pokemonData.sprites,
            };
          })
        );
        setPokemons(pokemonsData);
      } catch (error) {
        console.error("Error fetching Pokémon data:", error);
      }
    };
    fetchPokemons();
  }, [page]);

  const openModal = (pokemon) => {
    setSelectedPokemon(pokemon);
    setIsModalVisible(true);
  };

  const closeModal = () => {
    setIsModalVisible(false);
  };

  const previousPage = () => {
    if (page > 1) {
      setPage(page - 1);
    }
  };

  const nextPage = () => {
    setPage(page + 1);
  };

  return (
    <ScrollView>
      <View style={styles.page}>
        <Text style={{ fontSize: 30 }}>Pókemons</Text>
        <View style={styles.container}>
          {pokemons.map((pokemon, index) => (
            <View key={index} style={{ width: "50%", alignItems: "center" }}>
              <Text style={styles.text}>{pokemon.name}</Text>
              <Pressable onPress={() => openModal(pokemon)}>
                <Image
                  style={{ width: 120, height: 120 }}
                  source={{
                    uri: pokemon.image,
                  }}
                />
              </Pressable>
            </View>
          ))}
        </View>

        <View style={styles.containerButtons}>
          <Pressable onPress={previousPage} style={styles.button}>
            <Text style={styles.buttonText}>Anterior</Text>
          </Pressable>
          <Pressable onPress={nextPage} style={styles.button}>
            <Text style={styles.buttonText}>Siguiente</Text>
          </Pressable>
        </View>

        {/* Modal */}
        {selectedPokemon && (
          <Modal
            visible={isModalVisible}
            animationType="slide"
            onRequestClose={closeModal}
            transparent={true}
          >
            <View style={styles.modalContainer}>
              <Card
                img={selectedPokemon.image}
                name={selectedPokemon.name}
                sprites={selectedPokemon.details}
                back={closeModal}
                previous={() => {}}
                next={() => {}}
              />
            </View>
          </Modal>
        )}
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "row",
    flexWrap: "wrap",
    alignItems: "center",
    justifyContent: "center",
  },
  page: {
    marginTop: 35,
    position: "relative",
    justifyContent: "center",
    alignItems: "center",
  },
  text: {
    fontSize: 20,
    textAlign: "center",
  },
  button: {
    backgroundColor: "black",
    width: "30%",
    padding: 15,
    borderRadius: 10,
    alignItems: "center",
  },
  buttonText: {
    color: "white",
    fontWeight: "700",
    fontSize: 14,
  },
  containerButtons: {
    flex: 1,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
  },
  modalContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "white",
    marginTop: 200,
    marginBottom: 80,
  },
});
