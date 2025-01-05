import React, { useState } from "react";
import { Text, Image, View, Pressable, StyleSheet } from "react-native";

export default Card = ({ name, sprites, back }) => {
  const [imageIndex, setImageIndex] = useState(0);
  const images = [
    sprites.front_default,
    sprites.back_default,
    sprites.back_shiny,
    sprites.front_shiny,
  ];

  const handlePrevious = () => {
    setImageIndex((prevIndex) => (prevIndex === 0 ? images.length - 1 : prevIndex - 1));
  };

  const handleNext = () => {
    setImageIndex((prevIndex) => (prevIndex === images.length - 1 ? 0 : prevIndex + 1));
  };

  return (
    <View style={styles.page}>
      <View style={styles.container}>
        <View style={{ width: "50%", alignItems: "center" }}>
          <Text style={styles.text} onPress={back}>
            {name}
          </Text>
          <Image
            style={{
              width: 120,
              height: 120,
            }}
            source={{
              uri: images[imageIndex],
            }}
          />
        </View>
      </View>
      <View style={styles.buttons}>
        <Pressable onPress={handlePrevious} style={styles.button}>
          <Text style={styles.buttonText}>Anterior</Text>
        </Pressable>
        <Pressable onPress={handleNext} style={styles.button}>
          <Text style={styles.buttonText}>Siguiente</Text>
        </Pressable>
      </View>
    </View>
  );
};

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
    width: "40%",
    padding: 15,
    borderRadius: 10,
    alignItems: "center",
    marginTop: 40,
  },
  buttonText: {
    color: "white",
    fontWeight: "400",
    fontSize: 12,
  },
  buttons: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 50,
    flexDirection: "row",
  },
});
