import React from "react";

import { Container, Menu } from "semantic-ui-react";
import ProfileSign from "../layouts/ProfileSign"

export default function Navi() {

  return (
    <div>
      <Menu inverted fixed="top">
        <Container>
          <Menu.Item name="home"/>
          <Menu.Item name="messages" />
          <Menu.Menu position="right">
          </Menu.Menu>
        </Container>
      </Menu>
    </div>
  );
}
