import { useState } from "react";
import { Pressable, View, StyleSheet } from "react-native";

export default function App03() {
  const [size, setSize] = useState(200);
  const [color, setColor] = useState("yellow");

  const handlePress = (isUpperSquare) => {
    if (isUpperSquare) {
      setSize((prevSize) => prevSize + 50);
      setColor("green");
    } else {
      setSize((prevSize) => (prevSize > 100 ? prevSize - 50 : 100));
      setColor("yellow");
    }
  };

  return (
    <View style={styles.container}>
      <Pressable style={[styles.square, { width: size, height: size, backgroundColor: color }]} onPress={() => handlePress(true)} />
      <Pressable style={[styles.square, { width: size, height: size, backgroundColor: color }]} onPress={() => handlePress(false)} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
    alignItems: "center",
    justifyContent: "center",
  },
});
