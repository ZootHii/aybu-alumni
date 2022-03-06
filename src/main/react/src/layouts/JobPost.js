import { Component } from "react";
import { Form } from "semantic-ui-react";
import ApiRequests from "../utils/ApiRequests";

export class CreatePost extends Component {
  constructor(props) {
    super(props);

    this.state = {
      user: [],
      title: "",
      content: "",
      description: "",
      fileUrl: "",
      error: [],
    };
  }

  createPost = async (e) => {
    e.preventDefault();
    console.log(this.state.title);
    console.log(this.state.content);
    console.log(this.state.description);
    console.log(this.state.fileUrl);

    ApiRequests.createJobPost(
      this.state.title,
      this.state.content,
      this.state.description,
      this.state.fileUrl
    )
      .then((response) => {
        console.log(response);
      })
      .catch((err) => {
        console.log(err.response);
        this.setState({ error: err.response.data.data });
      });

    await this.timeOut(1000);

    console.log(this.state.error);
  };

  timeOut(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  render() {
    const { error, title, pageUrl, content, description, fileUrl } = this.state;
    return (
      <Form>
        <h1>Create Job Post</h1>
        <Form.Group widths="equal">
          <Form.Input
            label="Title"
            class="form-control"
            id="email"
            placeholder="Name"
            autoComplete="off"
            onChange={(e) => {
              this.setState({ title: e.target.value });
            }}
          />

          <Form.Input
            label="Content"
            class="form-control"
            id="content"
            placeholder="Content"
            autoComplete="off"
            onChange={(e) => {
              this.setState({ content: e.target.value });
            }}
          />
          <Form.Input
            label="FileUrl"
            class="form-control"
            id="fileUrl"
            placeholder="FileUrl"
            autoComplete="off"
            onChange={(e) => {
              this.setState({ fileUrl: e.target.value });
            }}
          />
        </Form.Group>

        <Form.TextArea
          label="Description"
          class="form-control"
          id="description"
          placeholder="Description"
          autoComplete="off"
          onChange={(e) => {
            this.setState({ description: e.target.value });
          }}
        />

        <Form.Button
          onClick={(e) => {
            this.createPost(e);
          }}
        >
          Share
        </Form.Button>
      </Form>
    );
  }
}

export default CreatePost;
