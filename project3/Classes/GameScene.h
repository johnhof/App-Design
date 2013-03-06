/*
 * GameScene.h
 *
 *  Created on: Mar 1, 2013
 *      Author: john
 */

#ifndef GAMESCENE_H_
#define GAMESCENE_H_

#include "cocos2d.h"

class GameScene : public cocos2d::CCLayer
{
public:
	GameScene();
	virtual ~GameScene();

    // Here's a difference. Method 'init' in cocos2d-x returns bool, instead of returning 'id' in cocos2d-iphone
    virtual bool init();

    //scene pointer
    static cocos2d::CCScene* scene();

    // implement the "static node()" method manually
    CREATE_FUNC(GameScene);

    // a selector callback
    void menuCloseCallback(CCObject* pSender);

    //cpp with cocos2d-x
    void spriteMoveFinished(CCNode* sender);

    //touches
    void ccTouchesBegan(cocos2d::CCSet* touches, cocos2d::CCEvent* event);
    void ccTouchesEnded(cocos2d::CCSet* touches, cocos2d::CCEvent* event);

    void update(float dt);

    void endGame();

    void reverseX();
    void reverseY();

    void updateVector();

protected:
    cocos2d::CCArray *_blocks;
    cocos2d::CCArray *_hBorders;
    cocos2d::CCArray *_vBorders;

    //there will only ever be one ball or paddle
    cocos2d::CCSprite* ball;
    cocos2d::CCSprite* paddle;
    cocos2d::CCLabelTTF*label;

    int ended;
    int score;
    int move;

    float xDir;
    float yDir;

    bool soundOn;
};

#endif /* GAMESCENE_H_ */
