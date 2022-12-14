import BottomNavBar from "../components/BottomNavBar";

export default function HowTo(props) {
    return (
        <div className="App">
            <header className="App-header">
                <div>RSS ๋ฑ๋ก ์๋ด ๐โ๏ธ</div>

                <br/>
                <div style={{fontSize: '0.85rem', marginBottom: 20}}>
                    MyRSS๋ ํฐ์คํ ๋ฆฌ, velog, Brunch, medium์ ์ง์ํ๊ณ  ์์ด์ <br/>
                    ๊ด์ฌ์๋ ๋ธ๋ก๊ทธ๋ฅผ ๋ฐ๊ฒฌํ์ ๋, ํธํ๊ฒ ์ฃผ์๋ฅผ ๋ถ์ฌ๋ฃ์ด์ฃผ์ธ์. <br/>
                    RSS ์ฃผ์๋ MyRSS๊ฐ ์ธ์ํด์ ์ฒ๋ฆฌํด๋๋ฆด๊ฒ์ ๐
                </div>
                <img id="example" src="/rss-example.gif" style={{width: '80%', maxWidth: 400, marginBottom: 50}}/>

                <br/>
                <img id="rules" src="/rss-howto2.webp"
                     style={{top: '40vh', width: '100%', maxWidth: 800, marginTop: 50, marginBottom: 150}}/>
            </header>
            <footer className="App-footer">
                <BottomNavBar
                    loginStatus={props.loginStatus}
                    userInfo={props.userInfo}
                    navIndex={props.navIndex}/>
            </footer>
        </div>
    );
}
