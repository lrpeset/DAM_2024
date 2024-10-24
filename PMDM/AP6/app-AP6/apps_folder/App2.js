import React, { useState } from "react";
import { Button, View, StyleSheet, Image } from "react-native";

export default function App2() {
  const [catImages, setCatImages] = useState([]);

  const getCatImages = async () => {
    const urls = [
      "https://api.thecatapi.com/v1/images/search?size=full",
      "https://api.thecatapi.com/v1/images/search?size=full",
    ];

    const res = await Promise.all(urls.map((url) => fetch(url)));
    const data = await Promise.all(res.map((res) => res.json()));

    setCatImages([data[0][0].url, data[1][0].url]);
  };

  return (
    <View style={styles.container}>
      {catImages.length > 0 &&
        catImages.map((image, index) => (
          <Image key={index} source={{ uri: image }} style={styles.image} />
        ))}
      <Button title="Mostrar dos gato" onPress={getCatImages} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
  },
  image: {
    width: 300,
    height: 300,
    marginTop: 20,
    borderRadius: 10,
  },
});
