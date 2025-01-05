import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { NavigationContainer } from "@react-navigation/native";
import Ejercicio1 from "./Ejercicio1/Ejercicio1";
import Ejercicio2 from "./Ejercicio2/Ejercicio2";

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator>
        <Tab.Screen name="Ejercicio 1" component={Ejercicio1} />
        <Tab.Screen name="Ejercicio 2" component={Ejercicio2} />
      </Tab.Navigator>
    </NavigationContainer>
  );
}
