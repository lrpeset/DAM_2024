import { StyleSheet, View } from "react-native";

export default function App4() {
  const filas = [
    ["red", "blue", "green"],
    ["blue", "green", "red"],
    ["green", "red", "blue"],
  ];

  return (
    <View style={styles.container}>
      {filas.map((fila, rowIndex) => (
        <View key={rowIndex} style={styles.row}>
          {fila.map((color, circleIndex) => (
            <View key={circleIndex} style={[styles.circulo, { backgroundColor: color }]} />
          ))}
        </View>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  row: {
    flexDirection: "row",
  },
  circulo: {
    width: 100,
    height: 100,
    borderRadius: 100 / 2,
    backgroundColor: "green",
  },
});
