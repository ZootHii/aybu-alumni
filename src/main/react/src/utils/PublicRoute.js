import React from 'react'
import { Redirect, Route } from 'react-router-dom'
import Utils from './Utils'



const PublicRoute = ({component: Component, ...rest}) => {
   return (
       <Route 
       {...rest}
       render = {props => {
        return   !Utils.displayToken() ? <Component {...props}/> 
           : <Redirect to={{pathname: "/"}} />
       }}
       />
   ) 
}

export default PublicRoute