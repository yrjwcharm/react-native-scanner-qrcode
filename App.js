/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Alert,NativeModules,TouchableOpacity, StyleSheet, Text, View} from 'react-native';

type Props = {};
export default class App extends Component<Props> {
  componentWillMount(): void {

  }
  _isEmpty(prams){
    if(prams===null||prams===""||prams===undefined){
      return true;
    }else{
      return false;
    }
  };
  //扫描
  _scanner=()=>{
    NativeModules.NaviModule.startActivityByClassName('com.rnerweimascanner.ScannerActivity').then(result=>{
      if(this._isEmpty(result)){
        Alert.alert('请选择二维码进行扫描');
        return;
      }
      Alert.alert(result);
    }).catch(error=>{
       Alert.alert(error);
    });
  }
  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity  activeOpacity={0.8} onPress={this._scanner}>
        <Text style={styles.welcome}>扫描</Text>
        </TouchableOpacity>

      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
