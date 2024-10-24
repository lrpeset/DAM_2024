import React, { useState } from "react";
import { View, SafeAreaView, StyleSheet, TextInput, Button, Alert, Text } from "react-native";

export default function App4() {
  const [inputValue, setInputValue] = useState("");
  const [stringsArray, setStringsArray] = useState([]);
  const [positionInput, setPositionInput] = useState([]);
  const [outputValue, setOutputValue] = useState([]);

  const handleAddString = () => {
    if (inputValue) {
      setStringsArray([...stringsArray, inputValue]);
      setInputValue("");
    } else {
      Alert.alert("Por favor, inserta al menos un carácter");
    }
  };

  const handleShowValueAtPosition = () => {
    const position = parseInt(positionInput);
    if (position >= 0 && position < stringsArray.length) {
      setOutputValue(stringsArray[position]);
    } else {
      Alert.alert("Posición fuera de rango");
    }
    setPositionInput("");
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.innerContainer}>
        <TextInput
          style={styles.input}
          placeholder="Inserta tu texto..."
          value={inputValue}
          onChangeText={setInputValue}
        />
        <Button title="PULSA..." onPress={handleAddString} />

        <TextInput
          style={styles.input}
          placeholder="Inserta posición array..."
          value={positionInput}
          onChangeText={setPositionInput}
        />
        <Button title="PULSA..." onPress={handleShowValueAtPosition} />

        {outputValue !== "" && <Text style={styles.outputText}>{outputValue}</Text>}
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  innerContainer: {
    width: "80%",
    alignItems: "center",
  },
  input: {
    height: 40,
    margin: 12,
    padding: 10,
    borderWidth: 1,
    width: "100%",
  },
  outputText: {
    fontSize: 30,
    marginVertical: 5,
  },
});
