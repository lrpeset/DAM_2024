import { useState } from "react";

import { Text, View, StyleSheet } from "react-native";

export default function App01() {
  const [textStates, setTextStates] = useState({
    1: "Text",
    2: "Text",
    3: "Text",
    4: "Text",
  });

  const handlePress = (id) => {
    setTextStates((prevState) => ({
      ...prevState,
      [id]: prevState[id] === "Text" ? "Changed text" : "Text",
    }));
  };

  return (
    <View style={styles.container}>
      <Text style={styles.text} onPress={() => handlePress(1)}>
        {textStates[1]}
      </Text>
      <Text style={styles.text} onPress={() => handlePress(2)}>
        {textStates[2]}
      </Text>
      <Text style={styles.text} onPress={() => handlePress(3)}>
        {textStates[3]}
      </Text>
      <Text style={styles.text} onPress={() => handlePress(4)}>
        {textStates[4]}
      </Text>
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
  text: {
    fontSize: 30,
    marginBottom: 20,
  },
});
