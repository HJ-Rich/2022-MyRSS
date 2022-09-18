function Home() {
    return (
        <div className="App">
            <header className="App-header">
                <button type="button">
                    <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.REACT_APP_GITHUB_OAUTH_CLIENT_ID}`}>
                        Sign in with Github
                    </a>
                </button>
            </header>
        </div>
    );
}

export default Home;
