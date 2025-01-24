import React from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import CalculadoraIMC from "./components/CalculadoraIMC";

const App = () => {
  return (
    <SafeAreaView style={styles.container}>
      <CalculadoraIMC />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
    padding: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
  },
});

export default App;
