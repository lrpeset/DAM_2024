import { StyleSheet, View } from "react-native";

export default function App3() {
  const filas = [["triangulo"], ["cuadrado"], ["cuadrado", "cuadrado"]];

  const renderFigura = (tipo, index) => {
    if (tipo === "triangulo") {
      return <View key={index} style={styles.triangulo} />;
    } else if (tipo === "cuadrado") {
      return <View key={index} style={styles.cuadrado} />;
    }
  };

  return (
    <View style={styles.container}>
      {filas.map((fila, rowIndex) => (
        <View key={rowIndex} style={styles.row}>
          {fila.map((elemento, elementIndex) => renderFigura(elemento, elementIndex))}
        </View>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  row: {
    flexDirection: "row",
  },
  cuadrado: {
    width: 100,
    height: 100,
    backgroundColor: "blue",
  },
  triangulo: {
    width: 0,
    height: 0,
    borderLeftWidth: 50,
    borderRightWidth: 50,
    borderBottomWidth: 100,
    borderLeftColor: "transparent",
    borderRightColor: "transparent",
    borderBottomColor: "blue",
  },
});
