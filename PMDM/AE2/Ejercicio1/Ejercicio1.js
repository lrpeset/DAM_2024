import React, { useState, useEffect } from "react";
import { Text, View, Pressable, StyleSheet, Image, Alert } from "react-native";

const API_URL = "https://rickandmortyapi.com/api/character";

const Card = ({ card, index, onPress, disabled }) => (
  <View style={{ padding: 3 }}>
    <Pressable
      style={{
        borderRadius: 8,
        justifyContent: "center",
        alignItems: "center",
        textAlignVertical: "center",
        width: 80,
        height: 80,
        backgroundColor: card.isRevealed || card.isMatched ? "white" : "blue",
        borderColor: card.isMatched ? "green" : "blue",
        borderWidth: 1,
      }}
      onPress={() => onPress(index)}
      disabled={disabled}
    >
      {(card.isRevealed || card.isMatched) && (
        <Image style={styles.tinyPhoto} source={{ uri: card.image }} />
      )}
    </Pressable>
  </View>
);

const GameBoard = ({ cards, onCardPress, matchedCards, gameState }) => (
  <View style={{ marginTop: 5 }}>
    <View style={{ flexDirection: "row", flexWrap: "wrap", justifyContent: "center" }}>
      {cards.map((card, index) => (
        <Card
          key={card.id}
          card={{ ...card, isMatched: matchedCards.includes(index) }}
          index={index}
          onPress={onCardPress}
          disabled={matchedCards.includes(index) || card.isRevealed || gameState !== "playing"}
        />
      ))}
    </View>
  </View>
);

export default function Ejercicio1() {
  const [gameState, setGameState] = useState("start");
  const [cards, setCards] = useState([]);
  const [selectedCards, setSelectedCards] = useState([]);
  const [matchedCards, setMatchedCards] = useState([]);
  const [attempts, setAttempts] = useState(0);
  const [level, setLevel] = useState(1);

  const fetchImages = async (groupSize) => {
    try {
      const response = await fetch(API_URL);
      const data = await response.json();
      const randomCharacters = data.results.sort(() => Math.random() - 0.5).slice(0, groupSize);
      const groups = Array.from({ length: groupSize }, (_, i) => randomCharacters[i]);
      const cards = groups
        .flatMap((char) => Array(level === 3 ? 3 : 2).fill(char))
        .sort(() => Math.random() - 0.5)
        .map((char, index) => ({
          id: index,
          name: char.name,
          image: char.image,
          isRevealed: true,
        }));
      setCards(cards);
      setGameState("revealing");
    } catch (error) {
      console.error("Error fetching characters:", error);
    }
  };

  useEffect(() => {
    if (gameState === "revealing") {
      setTimeout(() => {
        const hiddenCards = cards.map((card) => ({
          ...card,
          isRevealed: false,
        }));
        setCards(hiddenCards);
        setGameState("playing");
      }, 1000);
    }
  }, [gameState]);

  const handleCardPress = (index) => {
    if (selectedCards.length < (level === 3 ? 3 : 2) && !cards[index].isRevealed) {
      const updatedCards = [...cards];
      updatedCards[index].isRevealed = true;
      setSelectedCards([...selectedCards, index]);
      setCards(updatedCards);
    }
  };

  useEffect(() => {
    if (selectedCards.length === (level === 3 ? 3 : 2)) {
      const selectedCardsDetails = selectedCards.map((index) => cards[index]);
      const allMatch = selectedCardsDetails.every(
        (card) => card.name === selectedCardsDetails[0].name
      );

      if (allMatch) {
        setMatchedCards([...matchedCards, ...selectedCards]);
      } else {
        setAttempts(attempts + 1);
        if (attempts + 1 >= 3) {
          Alert.alert("Has perdido cometiendo 3 fallos en total.");
          setGameState("start");
          setLevel(1);
          return;
        }
      }

      setTimeout(() => {
        const updatedCards = [...cards];
        if (!allMatch) {
          selectedCards.forEach((index) => (updatedCards[index].isRevealed = false));
        }
        setCards(updatedCards);
        setSelectedCards([]);
      }, 1000);
    }

    if (matchedCards.length === cards.length && cards.length > 0) {
      if (level === 3) {
        Alert.alert("¡Has ganado!");
        setGameState("start");
        setLevel(1);
      } else {
        setLevel(level + 1);
        startNextLevel(level + 1);
      }
    }
  }, [selectedCards]);

  const startNextLevel = (nextLevel) => {
    const groupSize = nextLevel === 1 ? 6 : nextLevel === 2 ? 8 : 4;
    setMatchedCards([]);
    setSelectedCards([]);
    fetchImages(groupSize);
  };

  const startGame = () => {
    setLevel(1);
    setAttempts(0);
    setMatchedCards([]);
    setSelectedCards([]);
    fetchImages(6);
  };

  return (
    <View style={{ justifyContent: "center", alignSelf: "center", marginVertical: 80 }}>
      {gameState === "start" && (
        <Text style={{ fontSize: 45, fontWeight: "bold" }} onPress={startGame}>
          Memory
        </Text>
      )}
      {gameState !== "start" && (
        <View style={{ marginVertical: 80 }}>
          <Text style={{ fontSize: 45, fontWeight: "bold", marginLeft: 35 }}>
            Memory - Nivel {level}
          </Text>
          <GameBoard
            cards={cards}
            onCardPress={handleCardPress}
            matchedCards={matchedCards}
            gameState={gameState}
          />
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  tinyPhoto: {
    width: 80,
    height: 80,
  },
});
